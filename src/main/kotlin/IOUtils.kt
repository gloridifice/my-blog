import java.io.File
import java.io.FileWriter
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.pathString

fun Path.childPath(child: String): Path {
    return Path(
        this.pathString + if (this.pathString.endsWith('/')) "" else {
            "/"
        } + child
    )
}

/// remove the "static/" ahead
fun Path.serverPathString(): String {
    return "/" + this.toString().removePrefix("static/")
}

fun Path.hasChildren(): Boolean{
    val files = this.toFile().listFiles()
    return files != null && files.isNotEmpty()
}

fun writeJson(path: Path, content: String) {
    assert(path.endsWith(".json"))
    path.createParentDirectories()
    FileWriter(path.pathString).append(content).close()
}

fun File.isImage(): Boolean{
    val ext = arrayOf("jpg", "jpeg", "png", "gif", "webp")
    return this.isFile && ext.contains(this.extension)
}