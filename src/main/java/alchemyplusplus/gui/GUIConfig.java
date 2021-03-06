/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alchemyplusplus.gui;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.utility.ConfigManager;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

/**
 *
 * @author jakimfett
 */
public class GUIConfig extends GuiConfig
{
    public GUIConfig(GuiScreen guiScreen)
    {
        super(guiScreen,
                new ConfigElement(ConfigManager.appConfig.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                AlchemyPlusPlus.ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigManager.appConfig.toString()));
    }
}
