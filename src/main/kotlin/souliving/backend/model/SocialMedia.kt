package souliving.backend.model

import org.springframework.data.relational.core.mapping.Table

@Table("social_media")
data class SocialMedia(
    var id: Long? = null,
    var name: String,
    var link: String,
)
