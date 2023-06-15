package za.ac.iie.opsc7311.orbit_books

class FavBookData {

    var title: String? = null
    var img: String? = null


    constructor() {}

    constructor(title: String?,  img: String?) {
        this.title = title
        this.img = img

    }
}