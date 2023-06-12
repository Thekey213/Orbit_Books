package za.ac.iie.opsc7311.orbit_books

class BookData {
    /**set Data*/
    var title:String? = null
    var description:String? = null
    var img:String? = null
    var author:String? = null
    constructor(){}

    constructor(title: String?, description: String?, img: String?, author: String?) {
        this.title = title
        this.description = description
        this.img = img
        this.author = author
    }


}