package com.example.bite_api.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileNotFoundException

@Configuration
class FirebaseConfig {

    @Bean
    fun initializeFirebase(): FirebaseApp {
        val serviceAccount = FirebaseConfig::class.java.getResourceAsStream("/bite-app-2b849-firebase-adminsdk-g0pf4-58768b1346.json")
            ?: throw FileNotFoundException("Firebase config file not found")

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        return if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        } else {
            FirebaseApp.getInstance()
        }
    }
}
