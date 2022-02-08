-----------------------------------------------------------------------------------------
--
-- Lines
--
-----------------------------------------------------------------------------------------

-- https://docs.coronalabs.com/guide/programming/index.html
-- https://docs.coronalabs.com/guide/programming/01/index.html
-- https://docs.coronalabs.com/api/library/display/newRect.html
-- https://docs.coronalabs.com/api/library/display/newText.html
-- https://rgbcolorcode.com/

local levelManager = require( "scripts.level_manager" )

 -- Removes status bar on iOS
display.setStatusBar( display.HiddenStatusBar ) 

-- Removes bottom bar on Android 
if system.getInfo( "androidApiLevel" ) and system.getInfo( "androidApiLevel" ) < 19 then
	native.setProperty( "androidSystemUiVisibility", "lowProfile" )
else
	native.setProperty( "androidSystemUiVisibility", "immersiveSticky" ) 
end

levelManager.loadLevel(1)