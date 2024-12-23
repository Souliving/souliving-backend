package souliving.backend.model.image


import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Lob
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("photos")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    var name: String,
    var type: String,
    @Lob
    @Column(name = "container", length = 1000)
    var container: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (!container.contentEquals(other.container)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + container.contentHashCode()
        return result
    }
}
