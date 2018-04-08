package com.prangbi.android.icoco.model.ico

/**
 * Created by hsg on 2018. 2. 7..
 */
data class IcoProfile(
        var id: Long = 0L,
        var name: String? = null,
        var rating: Float = 0f,
        var ratingTeam: Float = 0f,
        var ratingVision: Float = 0f,
        var ratingProduct: Float = 0f,
        var ratingProfile: Float = 0f,
        var url: String? = null,
        var tagline: String? = null,
        var intro: String? = null,
        var about: String? = null,
        var logo: String? = null,
        var country: String? = null,
        var notification: String? = null,
        var registration: String? = null,
        var restrictions: List<IcoRestriction>? = null,
        var milestones: List<IcoMilestone>? = null,
        var teamIntro: String? = null,
        var links: IcoLinks? = null,
        var finance: IcoFinance? = null,
        var dates: IcoDates? = null,
        var team: List<IcoTeam>? = null,
        var ratings: List<IcoRating>? = null,
        var categories: List<IcoCategory>? = null,
        var exchanges: List<IcoExchange>? = null
)

data class IcoRestriction(
        var country: String? = null
)

data class IcoMilestone(
        var title: String? = null,
        var content: String? = null
)

data class IcoLinks(
        var twitter: String? = null,
        var slack: String? = null,
        var telegram: String? = null,
        var facebook: String? = null,
        var medium: String? = null,
        var bitcointalk: String? = null,
        var github: String? = null,
        var reddit: String? = null,
        var discord: String? = null,
        var video: String?  = null,
        var www: String? = null,
        var whitepaper: String? = null
)

data class IcoFinance(
        var token: String? = null,
        var price: String? = null,
        var bonus: Boolean = false,
        var tokens: Long = 0L,
        var tokentype: String? = null,
        var hardcap: String? = null,
        var softcap: String? = null,
        var raised: Int = 0,
        var platform: String? = null,
        var distributed: String? = null,
        var minimum: String? = null,
        var accepting: String? = null
)

data class IcoTeam(
        var name: String? = null,
        var title: String? = null,
        var socials: List<IcoSocial>? = null,
        var group: String? = null,
        var photo: String? = null,
        var iss: Float? = null
)

data class IcoSocial(
        var site: String? = null,
        var url: String? = null
)

data class IcoRating(
        var date: String? = null,
        var name: String? = null,
        var title: String? = null,
        var photo: String? = null,
        var team: Int = 0,
        var vision: Int = 0,
        var product: Int = 0,
        var profile: Float = 0f,
        var review: String? = null,
        var weight: String? = null,
        var agree: Int = 0
)

data class IcoCategory(
        var id: Long = 0L,
        var name: String? = null
)

data class IcoExchange(
        var id: Long = 0L,
        var name: String? = null,
        var logo: String? = null,
        var price: Float = 0f,
        var currency: String? = null,
        var roi: String? = null
)