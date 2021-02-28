package org.learning.caching.mockserver

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.learning.caching.gateway.DemogProfile

class CustomerServiceServer(port: Int) : WireMockServer(port) {
    private val objectMapper = ObjectMapper()

    fun okResponseFor(customerId: String, demogProfile: DemogProfile) {
        this.stubFor(
            get(
                urlEqualTo("/customers/$customerId/profile")
            )
                .willReturn(
                    WireMock.okJson(objectMapper.writeValueAsString(demogProfile))
                )
        )
    }
}