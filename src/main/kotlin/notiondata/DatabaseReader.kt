package notiondata

import Post
import childPath
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import hasChildren
import notion.api.v1.NotionClient
import notion.api.v1.json.GsonSerializer
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.BookmarkBlock
import notion.api.v1.model.blocks.ImageBlock
import notion.api.v1.model.databases.Database
import notion.api.v1.model.databases.QueryResults
import notion.api.v1.model.pages.Page
import java.nio.file.Path
import java.text.SimpleDateFormat
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile
import kotlin.io.path.name
import kotlin.io.path.pathString


fun readNotionData(rootPath: Path): DataDatabase {
    val queryDatabaseJson = rootPath.childPath("query_result.json").toFile().readText()
    val result = NotionClient.defaultJsonSerializer.toQueryResults(queryDatabaseJson)

    val databaseJson = rootPath.childPath("database.json").toFile().readText()
    val database = NotionClient.defaultJsonSerializer.toDatabase(databaseJson)

    var dataPages = result.results.map {
        DataPage(it, rootPath)
    }.toList()
    dataPages = dataPages.sortedWith { n1, n2 ->
        val fmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val n1Date = fmt.parse(n1.page.lastEditedTime)
        val n2Date = fmt.parse(n2.page.lastEditedTime)
        -n1Date.compareTo(n2Date)
    }
    val rawPages = dataPages
    dataPages = dataPages.filter { it.post.published }

    return DataDatabase(database, result, dataPages, rawPages)
}

class DataDatabase(
    val database: Database,
    val queryDatabaseRequest: QueryResults,
    val dataPages: List<DataPage>,
    val rawPages: List<DataPage> // contains un published pages
) {
}

class DataPage(
    val page: Page,
    parentPath: Path,
) {
    val dataBlocks: List<DataBlock>?
    val post: Post

    init {
        //read dataBlocks object
        val currentPath = parentPath.childPath(page.id)
        dataBlocks = readChildrenBlocks(currentPath)
        post = Post(page)
    }
}

open class DataBlock(val block: Block, parentPath: Path) {
    val children: List<DataBlock>?;

    init {
        val currentPath = parentPath.childPath(block.id!!)
        children = readChildrenBlocks(currentPath)
    }
}

data class Image(val byteArray: ByteArray, val name: String)
class ImageDataBlock(block: Block, parentPath: Path) : DataBlock(block, parentPath) {
    val image: Image

    init {
        val file = parentPath.toFile().listFiles()!!.filter {
            it.name.startsWith("img_${block.id!!}")
        }[0]
        image = Image(file.readBytes(), file.name)
    }
}

class BookmarkDataBlock(block: Block, parentPath: Path) : DataBlock(block, parentPath) {
    val title: String?

    init {
        val filePath = parentPath.childPath("bookmark_${block.id}.json")
        title = if (filePath.exists()) {
            val json = filePath.toFile().readText()
            val jsonObject = Gson().fromJson(json, JsonObject::class.java)
            if (jsonObject.has("title"))
                jsonObject.get("title").asString
            else null
        } else null
    }
}

fun readChildrenBlocks(currentPath: Path): List<DataBlock>? {
    val files = currentPath.toFile().listFiles()
    val dataBlocks = if (currentPath.hasChildren() && files != null) {
        val handledFiles = files.filter { file ->
            file != null && file.isFile && file.name.endsWith(".json") && file.name.split('_').first()
                .toIntOrNull() != null
        }.sortedBy { it.name.split('_').first().toInt() }
        val list = ArrayList<DataBlock>()
        for (file in handledFiles) {
            val read = file.readText()
            val block = NotionClient.defaultJsonSerializer.toBlock(read)
            val dataBlock =
                when (block) {
                    is ImageBlock -> ImageDataBlock(block, currentPath)
                    is BookmarkBlock -> BookmarkDataBlock(block, currentPath)
                    else -> DataBlock(block, currentPath)
                }
            list.add(dataBlock)
        }
        list
    } else null
    return dataBlocks
}
