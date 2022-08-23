package info.isaaclee.lolgoitne.file

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import info.isaaclee.lolgoitne.core.application.port.out.http.FindChampionOutPort
import info.isaaclee.lolgoitne.core.application.port.out.http.FindQueueOutPort
import info.isaaclee.lolgoitne.core.domain.riot.Champion
import info.isaaclee.lolgoitne.core.domain.riot.Queue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class RiotFileReader: FindChampionOutPort, FindQueueOutPort {
  override fun findChampion(championId: String): Champion? {
    val mapper = jacksonObjectMapper()
    val champions = mapper.readValue<Map<String, Champion>>(
      ClassPathResource("champions.json").inputStream,
      mapper.typeFactory.constructMapType(Map::class.java, String::class.java, Champion::class.java)
    )
    for (champion in champions.entries.iterator()) {
      if (champion.value.key == championId) {
        return champion.value
      }
    }
    return null
  }
  
  override fun findQueue(queueId: Long): Queue? {
    val mapper = jacksonObjectMapper()
    val queues = mapper.readValue(ClassPathResource("queues.json").inputStream, QueueDTO::class.java).data
    for (queue in queues) {
      if (queue.queueId == queueId) {
        return queue
      }
    }
    return null
  }
  
  data class QueueDTO(
    val data: List<Queue>
  )
}
