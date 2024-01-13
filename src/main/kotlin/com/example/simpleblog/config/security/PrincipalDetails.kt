package com.example.simpleblog.config.security

import com.example.simpleblog.domain.member.Member
import mu.KotlinLogging
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * Created by mskwon on 2024/01/13.
 */
class PrincipalDetails(
    member: Member
): UserDetails {

    private val log = KotlinLogging.logger {  }

    var member: Member = member
        private set

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        log.info { "Role 검증" }
        val collection: MutableCollection<GrantedAuthority> = ArrayList()
        collection.add(GrantedAuthority { "ROLE_" + member.role })
        return collection
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.email
    }

    /**
     * User 계정 만료 사용 여부
     * @return Boolean
     */
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}