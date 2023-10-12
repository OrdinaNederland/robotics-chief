package nl.ordina.robotics.server.ssh.commands

import nl.ordina.robotics.server.robot.CommandExecutor
import nl.ordina.robotics.server.robot.Robot
import nl.ordina.robotics.server.socket.Message
import nl.ordina.robotics.server.socket.Topic
import nl.ordina.robotics.server.socket.Topics
import nl.ordina.robotics.server.ssh.Cmd

suspend fun Robot.listTopics(executor: CommandExecutor): Message {
    val topicNames = executor.executeCommand(
        id,
        Cmd.Ros.sourceBash,
        Cmd.Ros.listTopics(settings.domainId),
    )
        .split('\n')
        .filter { it.startsWith('/') }

    val topicInfos = topicNames.associateWith {
        executor.executeCommand(
            id,
            Cmd.Ros.sourceBash,
            Cmd.Ros.topicInfo(settings.domainId, it),
        )
            .split("/n")
            .first()
            .split("\n")
            .first()
            .split(": ")
            .last()
    }

    val topics = topicNames.map { id ->
        Topic(id, topicInfos[id]!!, -1)
    }

    return Topics(topics)
}
