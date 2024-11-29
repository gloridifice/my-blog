package htmlgen.component

import htmlgen.model.BlogPostPage
import htmlgen.model.DevLogPostPage
import kotlinx.html.*
import htmlgen.toNormalString
import serverPathString

fun FlowContent.blogPostPreview(blogPost: BlogPostPage) {
    div {
        classes += "post_preview"
        classes += "regular"
        classes += "reveal"
        onClick = "location.href='${blogPost.htmlServerPath}';"

        val emoji = blogPost.getEmoji()
        div {
            classes += "emoji"
            +emoji
        }

        div {
            classes += "description"
            h2 {
                classes += "title"
                +blogPost.getPlainTitle()
            }
            h3 {
                classes += "slug"
                + if (blogPost.slug != null) blogPost.slug.toNormalString() else "没有介绍"
            }
        }

        div {
            classes += "info"
            p {
                classes += "date"
                +blogPost.getPreviewDisplayDate()
            }
            p {
                classes += "type"
                +blogPost.type.name!!
            }
        }
    }
}

fun FlowContent.devLogPostPreview(devLogPost: DevLogPostPage){
    div {
        classes += "dev_log_post_preview"
        classes += "regular"
        classes += "reveal"
        onClick = "location.href='${devLogPost.htmlServerPath}';"

        val emoji = devLogPost.getEmoji()
        val imageUrl = devLogPost.previewImagePath?.serverPathString();
        if (imageUrl != null){
            div {
                classes += "preview"
                img {
                    src = imageUrl
                }
            }
        }else{
            div {
                classes += "emoji"
                +emoji
            }
        }

        div {
            classes += "description"
            h2 {
                classes += "title"
                +devLogPost.getPlainTitle()
            }
        }

        div {
            classes += "info"
            p {
                classes += "date"
                +devLogPost.getPreviewDisplayDate()
            }
        }
    }
}