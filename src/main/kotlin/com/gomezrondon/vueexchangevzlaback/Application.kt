package com.gomezrondon.vueexchangevzlaback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

@RestController
@CrossOrigin(origins = [ "\${settings.cors_origin}" ])
class Controller(val repository: VzlaExchangeRateRepository) {

	@GetMapping("/time")
	fun getTime(): String {
		return LocalDateTime.now().toString()
	}

	@PostMapping("v1/exchange")
	fun saveExchangeRate(@RequestBody exchange: VzlaExchangeRate) {
		repository.save(exchange)
	}

	@GetMapping("v1/exchange/{ipAddress}")
	fun getExchangeRate(@PathVariable ipAddress: String): VzlaExchangeRate {
		val vzlaExchangeRate = repository.findFirstByIpAddressOrderByIdDesc(ipAddress)
		return vzlaExchangeRate
	}

}

data class Test(val vzlRate:String)


@Document
data class VzlaExchangeRate(@Id var id: String?, val vzlRate:String
, val TicoRate:String
, val ipAddress:String
, val dateTime: String = LocalDateTime.now().toString())

interface VzlaExchangeRateRepository : CrudRepository<VzlaExchangeRate, String> {
	fun findFirstByIpAddressOrderByIdDesc(IpAddress: String): VzlaExchangeRate
}