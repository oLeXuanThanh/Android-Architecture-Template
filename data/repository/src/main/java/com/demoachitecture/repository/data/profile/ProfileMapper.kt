package com.demoachitecture.repository.data.profile

import com.demoachitecture.domain.profile.Profile
import com.demoachitecture.local.data.profile.ProfileEntity
import com.demoachitecture.remote.data.profile.ProfileModel

fun ProfileModel.toProfileEntity(): ProfileEntity {
    return ProfileEntity()
}

fun ProfileEntity.toProfile(): Profile {
    return Profile()
}