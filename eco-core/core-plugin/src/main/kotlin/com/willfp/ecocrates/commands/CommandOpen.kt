package com.willfp.ecocrates.commands

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.command.impl.Subcommand
import com.willfp.ecocrates.crate.Crates
import com.willfp.ecocrates.crate.OpenMethod
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class CommandOpen(plugin: EcoPlugin) : Subcommand(
    plugin,
    "open",
    "ecocrates.command.open",
    true
) {
    override fun onExecute(player: CommandSender, args: List<String>) {
        player as Player

        if (args.isEmpty()) {
            player.sendMessage(plugin.langYml.getMessage("must-specify-crate"))
            return
        }

        val crate = Crates.getByID(args[0])

        if (crate == null) {
            player.sendMessage(plugin.langYml.getMessage("invalid-crate"))
            return
        }

        crate.openWithMethod(player, OpenMethod.VIRTUAL_KEY)
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String> {
        val completions = mutableListOf<String>()

        if (args.isEmpty()) {
            return Crates.values().map { it.id }
        }

        if (args.size == 1) {
            StringUtil.copyPartialMatches(
                args[0],
                Crates.values().map { it.id },
                completions
            )

            return completions
        }

        return emptyList()
    }
}
