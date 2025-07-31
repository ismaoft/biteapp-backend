package com.example.bite_api.services

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GoogleMapsService {

    private val apiKey = "AIzaSyBZJsoUsX5ue_Rc8ytPy84W_y0wm8HqSqQ"

    // Método para calcular la distancia entre dos coordenadas
    fun calcularDistancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {
        val restTemplate = RestTemplate()
        val url = "https://maps.googleapis.com/maps/api/distancematrix/json" +
                "?origins=$lat1,$lon1&destinations=$lat2,$lon2&key=$apiKey"

        val response = restTemplate.getForObject(url, Map::class.java)
        val rows = response?.get("rows") as? List<*>
        val elements = rows?.get(0) as? Map<*, *>
        val distance = (elements?.get("elements") as? List<*>)?.get(0) as? Map<*, *>

        return distance?.get("distance")?.let { (it as Map<*, *>)["text"].toString() } ?: "No disponible"
    }
}
