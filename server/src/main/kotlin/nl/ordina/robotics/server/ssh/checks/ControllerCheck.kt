package nl.ordina.robotics.server.ssh.checks

import nl.ordina.robotics.server.robot.CommandExecutor
import nl.ordina.robotics.server.robot.Robot
import nl.ordina.robotics.server.socket.StatusLine
import nl.ordina.robotics.server.ssh.Cmd

suspend fun Robot.controllerCheck(executor: CommandExecutor): StatusLine {
    val controllers = executor.executeCommand(id, Cmd.Bluetooth.paired)

    return StatusLine(
        name = "Controller",
        success = controllers.isNotEmpty(),
        pending = false,
        message = controllers.ifEmpty { "No bluetooth devices connected." },
        actionUrl = "/actions/modal?resource=bluetooth",
        actionLabel = "Pair & Connect",
    )
}
