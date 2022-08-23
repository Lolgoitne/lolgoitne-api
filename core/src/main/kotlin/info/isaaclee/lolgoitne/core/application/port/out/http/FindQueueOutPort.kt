package info.isaaclee.lolgoitne.core.application.port.out.http

import info.isaaclee.lolgoitne.core.domain.riot.Queue

interface FindQueueOutPort {
  fun findQueue(queueId: Long): Queue?
}
