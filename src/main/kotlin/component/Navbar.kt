package component

import kotlinx.html.*
import unsafeSVG

class NavbarItem(val title: String, val link: String, val iconSVGString: String, val isLinkOutside: Boolean = false) {

}

//todo implement link
fun FlowContent.navbar(items: List<NavbarItem>) {
    div {
        classes += "header"
        div {
            classes += "navbar"
            for (item in items) {
                navbarItem(item)
            }
            div {
                classes += "dark_mode"
                classes += "switch_visual_mode_button"
                unsafeSVG(
                    "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" fill=\"none\" version=\"1.1\" width=\"24\" height=\"23.999996185302734\" viewBox=\"0 0 24 23.999996185302734\"><defs><clipPath id=\"master_svg0_9_0221\"><rect x=\"0\" y=\"0\" width=\"24\" height=\"23.999996185302734\" rx=\"0\"/></clipPath></defs><g clip-path=\"url(#master_svg0_9_0221)\"><g><path d=\"M12,0C12.5523,0,13,0.447715,13,1C13,1,13,3,13,3C13,3.55228,12.5523,4,12,4C11.4477,4,11,3.55228,11,3C11,3,11,1,11,1C11,0.447715,11.4477,0,12,0C12,0,12,0,12,0ZM3.51289,3.51289C3.90342,3.12237,4.53658,3.12237,4.92711,3.51289C4.92711,3.51289,6.34711,4.93289,6.34711,4.93289C6.73763,5.32342,6.73763,5.95658,6.34711,6.34711C5.95658,6.73763,5.32342,6.73763,4.93289,6.34711C4.93289,6.34711,3.51289,4.92711,3.51289,4.92711C3.12237,4.53658,3.12237,3.90342,3.51289,3.51289C3.51289,3.51289,3.51289,3.51289,3.51289,3.51289ZM20.4871,3.51289C20.8776,3.90342,20.8776,4.53658,20.4871,4.92711C20.4871,4.92711,19.0671,6.34711,19.0671,6.34711C18.6766,6.73763,18.0434,6.73763,17.6529,6.34711C17.2624,5.95658,17.2624,5.32342,17.6529,4.93289C17.6529,4.93289,19.0729,3.51289,19.0729,3.51289C19.4634,3.12237,20.0966,3.12237,20.4871,3.51289C20.4871,3.51289,20.4871,3.51289,20.4871,3.51289ZM12,8C9.79086,8,8,9.79086,8,12C8,14.2091,9.79086,16,12,16C14.2091,16,16,14.2091,16,12C16,9.79086,14.2091,8,12,8C12,8,12,8,12,8ZM6,12C6,8.68629,8.68629,6,12,6C15.3137,6,18,8.68629,18,12C18,15.3137,15.3137,18,12,18C8.68629,18,6,15.3137,6,12C6,12,6,12,6,12ZM0,12C0,11.4477,0.447715,11,1,11C1,11,3,11,3,11C3.55228,11,4,11.4477,4,12C4,12.5523,3.55228,13,3,13C3,13,1,13,1,13C0.447715,13,0,12.5523,0,12C0,12,0,12,0,12ZM20,12C20,11.4477,20.4477,11,21,11C21,11,23,11,23,11C23.5523,11,24,11.4477,24,12C24,12.5523,23.5523,13,23,13C23,13,21,13,21,13C20.4477,13,20,12.5523,20,12C20,12,20,12,20,12ZM6.34711,17.6529C6.73763,18.0434,6.73763,18.6766,6.34711,19.0671C6.34711,19.0671,4.92711,20.4871,4.92711,20.4871C4.53658,20.8776,3.90342,20.8776,3.51289,20.4871C3.12237,20.0966,3.12237,19.4634,3.51289,19.0729C3.51289,19.0729,4.93289,17.6529,4.93289,17.6529C5.32342,17.2624,5.95658,17.2624,6.34711,17.6529C6.34711,17.6529,6.34711,17.6529,6.34711,17.6529ZM17.6529,17.6529C18.0434,17.2624,18.6766,17.2624,19.0671,17.6529C19.0671,17.6529,20.4871,19.0729,20.4871,19.0729C20.8776,19.4634,20.8776,20.0966,20.4871,20.4871C20.0966,20.8776,19.4634,20.8776,19.0729,20.4871C19.0729,20.4871,17.6529,19.0671,17.6529,19.0671C17.2624,18.6766,17.2624,18.0434,17.6529,17.6529C17.6529,17.6529,17.6529,17.6529,17.6529,17.6529ZM12,20C12.5523,20,13,20.4477,13,21C13,21,13,23,13,23C13,23.5523,12.5523,24,12,24C11.4477,24,11,23.5523,11,23C11,23,11,21,11,21C11,20.4477,11.4477,20,12,20C12,20,12,20,12,20Z\" fill-rule=\"evenodd\" fill=\"#373737\" fill-opacity=\"1\"/></g></g></svg>\n"
                )
            }
        }
    }
}

private fun FlowContent.navbarItem(item: NavbarItem) {
    div {
        classes += "navbar_item"
        onClick = if (item.isLinkOutside){
            "window.open('${item.link}')"
        }else{
            "window.location='${item.link}'"
        }
        unsafeSVG(item.iconSVGString)
        div {
            classes += "title"
            +item.title
        }
    }
}
