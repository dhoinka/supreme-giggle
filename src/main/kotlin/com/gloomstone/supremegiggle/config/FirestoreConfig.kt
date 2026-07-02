package com.gloomstone.supremegiggle.config

import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FirestoreConfig {

    @Bean(destroyMethod = "close")
    fun firestore(): Firestore {
        return FirestoreOptions.getDefaultInstance()
            .service
    }
}


