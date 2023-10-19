//package nl.ordina.robotics.server.command
//
//import nl.ordina.robotics.server.robot.RobotId
//import nl.ordina.robotics.server.socket.CommandFailure
//import nl.ordina.robotics.server.socket.CommandSuccess
//import nl.ordina.robotics.server.socket.Message
//import nl.ordina.robotics.server.ssh.Cmd
//import nl.ordina.robotics.server.robot.CommandExecutor
//import nl.ordina.robotics.server.ssh.ignoreFailure
//import org.springframework.http.ResponseEntity
//
////@RestController("/command")
//class CommandController(
////    @Autowired
//    private val executor: CommandExecutor,
//) {
//
//////    @PostMapping("/robot/{robotId}/setupenv")
//    suspend fun setupEnv( robotId: String): ResponseEntity<Message> {
//        val output = executor.executeCommand(
//            RobotId(robotId),
//            Cmd.Unix.addToBashRc(Cmd.Ros.sourceBash),
//        )
//
//        return respondCommand(
//            command = "Command.SetupEnv",
//            success = true,
//            message = "Setup output: $output",
//        )
//    }
//
////    @PostMapping("/robot/{robotId}/clone")
//    suspend fun clone( robotId: String): ResponseEntity<Message> {
//        val output = executor.executeCommand(
//            RobotId(robotId),
//
//            Cmd.Git.clone(
//                "https://github.com/OrdinaNederland/robotics-workshop",
//                "/home/jetson/robotics-workshop",
//            ),
//        )
//
//        return respondCommand(
//            command = "Command.Clone",
//            success = true,
//            message = "Clone output: $output",
//        )
//    }
//
////    @PostMapping("/robot/{robotId}/pull")
//    suspend fun pull( robotId: String): ResponseEntity<Message> {
//        val output = executor.executeCommand(
//            RobotId(robotId),
//            Cmd.Git.pull,
//        )
//
//        return respondCommand(
//            command = "Command.Pull",
//            success = true,
//            message = "Pull output: $output",
//        )
//    }
//
////    @PostMapping("/robot/{robotId}/build")
//    suspend fun build( robotId: String): ResponseEntity<Message> {
//        val output = executor.executeInWorkDir(
//            RobotId(robotId),
//            Cmd.Ros.buildInstall,
//            Cmd.Ros.sourceLocalSetup,
//        )
//
//        return respondCommand(
//            command = "Command.Build",
//            success = output.contains("Failed to connect"),
//            message = "Build output: $output",
//        )
//    }
//
////    @PostMapping("/robot/{robotId}/connect/{controllerId}")
//    suspend fun connectController(
//         robotId: String,
//         controllerId: String,
//    ): ResponseEntity<Message> {
//        val output = executor.executeCommand(RobotId(robotId), Cmd.Bluetooth.connect(controllerId))
//
//        return respondCommand(
//            command = "Command.BluetoothConnect",
//            success = !output.contains("Failed to connect"),
//            message = output,
//        )
//    }
//
////    @PostMapping("/robot/{robotId}/disconnect/{controllerId}")
//    suspend fun disconnectController(
//         robotId: String,
//         controllerId: String,
//    ): ResponseEntity<Message> {
//        val output = executor.executeCommand(RobotId(robotId), Cmd.Bluetooth.disconnect(controllerId))
//
//        return respondCommand(
//            command = "Command.BluetoothDisconnect",
//            success = !output.contains("Successfully disconnected"),
//            message = output,
//        )
//    }
//
////    @PostMapping("/robot/{robotId}/restart/{number}")
//    suspend fun restart(
//         robotId: String,
//         number: String,
//    ): ResponseEntity<Message> {
//        val output = executor.executeInWorkDir(
//            RobotId(robotId),
//            Cmd.Ros.stop.ignoreFailure(),
//            Cmd.Ros.sourceBash,
//            Cmd.Ros.sourceLocalSetup,
//            Cmd.Ros.launch(number.toInt().coerceIn(1..100)),
//        )
//
//        return respondCommand(
//            command = "Command.RestartRos",
//            success = true,
//            message = "Launched robot number $number: $output",
//        )
//    }
//
////    @PostMapping("/robot/{robotId}/launch/{number}")
//    suspend fun launch(
//         robotId: String,
//         number: String,
//    ): ResponseEntity<Message> {
//        val output = executor.executeInWorkDir(
//            RobotId(robotId),
//            Cmd.Ros.sourceBash,
//            Cmd.Ros.sourceLocalSetup,
//            Cmd.Ros.launch(number.toInt().coerceIn(1..100)),
//        )
//
//        return respondCommand(
//            command = "Command.StartRos",
//            success = true,
//            message = "Launched robot number $number: $output",
//        )
//    }
//
////    @GetMapping("/robot/{robotId}/exec/{command}")
//    suspend fun exec(
//         robotId: String,
//         command: String,
//    ): ResponseEntity<Message> {
//        val output = executor.executeCommand(RobotId(robotId), command)
//
//        return respondCommand(
//            command = "Command.Exec",
//            success = true,
//            message = "Output: $output",
//        )
//    }
//}
//
//private fun respondCommand(
//    command: String,
//    success: Boolean,
//    message: String,
//): ResponseEntity<Message> {
//    val (status, result) = if (success) {
//        Pair(200, CommandSuccess(command, message))
//    } else {
//        Pair(500, CommandFailure(command, message))
//    }
//
//    return ResponseEntity
//        .status(status)
//        .body(result)
//}