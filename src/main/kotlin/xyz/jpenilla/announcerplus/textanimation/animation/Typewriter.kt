/*
 * This file is part of AnnouncerPlus, licensed under the MIT License.
 *
 * Copyright (c) 2020-2021 Jason Penilla
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package xyz.jpenilla.announcerplus.textanimation.animation

import org.bukkit.entity.Player
import org.koin.core.inject
import xyz.jpenilla.announcerplus.config.ConfigManager
import xyz.jpenilla.announcerplus.textanimation.TextAnimation

class Typewriter(private val player: Player?, private val text: String, private val ticks: Int) : TextAnimation {
  private val configManager: ConfigManager by inject()
  private var index = 0
  private var ticksLived = 0
  private var showUnderscore = true

  override fun getValue(): String {
    val s = try {
      configManager.parse(player, text).substring(0, index)
    } catch (e: Exception) {
      // if the placeholders changed in a way that causes us to out of bounds
      index = 0
      configManager.parse(player, text).substring(0, index)
    }
    return "$s${if (showUnderscore) "_" else " "}"
  }

  override fun nextValue(): String {
    ticksLived++
    if (ticksLived % 5 == 0) {
      showUnderscore = !showUnderscore
    }
    if (ticksLived % ticks == 0) {
      index++
      if (index > configManager.parse(player, text).length) {
        index = 0
      }
    }
    return getValue()
  }
}
