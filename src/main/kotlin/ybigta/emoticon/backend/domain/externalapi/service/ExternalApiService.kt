package ybigta.emoticon.backend.domain.externalapi.service

import org.springframework.stereotype.Service
import ybigta.emoticon.backend.infra.karloapi.KarloApiClient
import ybigta.emoticon.backend.infra.karloapi.request.KarloApiT2iRequest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Service
class ExternalApiService(
    private val karloApiClient: KarloApiClient,
) {
    @OptIn(ExperimentalEncodingApi::class)
    fun base64StringToByteArray(base64String: String): ByteArray {
        return Base64.decode(base64String)
    }

    fun inferT2iByKarloApi(prompt: String): ByteArray {
        val request = KarloApiT2iRequest(
            prompt = prompt,
            returnType = KarloApiT2iRequest.ReturnType.BASE64_STRING,
        )

        return karloApiClient
            .t2i(request)
            .images
            .first()
            .image
            .let { base64StringToByteArray(it) }
    }
}
