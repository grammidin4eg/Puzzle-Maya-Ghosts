local composer = require ("composer")
local common = require("scripts.common")
local render = require("scripts.render")

local scene = composer.newScene()
-- https://github.com/coronalabs/Match-Three-Space-RPG/blob/master/scene/menu.lua
function scene:create( event )
    print('menu create')
    local sceneGroup = self.view

    -- логотип
    local logo = display.newImageRect(sceneGroup, 'logo.png', 449, 391)
    logo.x = display.contentCenterX
    logo.y = display.contentCenterY - 150

    -- текст снизу
    local tapText = display.newText( sceneGroup, 'tap to play', display.contentCenterX, display.contentCenterY + 190, native.systemFont, 50)
    tapText:setFillColor( {
        type="gradient",
        color1={ 0.5, 0.5, 1 }, color2={ 1.00,0.55,0.10 }, direction="down"
    } )
    render.onFadeIn(tapText)

    -- музыка
    local backgroundMusic = audio.loadStream( "snd/main_menu.mp3" )
    audio.play( backgroundMusic, { channel=1, loops=-1 } )

    -- обработка тапа
    function sceneGroup:tap()
        composer.gotoScene( "game", { effect = "fade", params = { } })
      end
    sceneGroup:addEventListener("tap")
end

-- show()
function scene:show( event )
 
    local sceneGroup = self.view
    local phase = event.phase
 
    if ( phase == "will" ) then
        -- Code here runs when the scene is still off screen (but is about to come on screen)
 
    elseif ( phase == "did" ) then
        -- Code here runs when the scene is entirely on screen
    end
end
 
 
-- hide()
function scene:hide( event )
 
    local sceneGroup = self.view
    local phase = event.phase
 
    if ( phase == "will" ) then
        -- Code here runs when the scene is on screen (but is about to go off screen)
 
    elseif ( phase == "did" ) then
        -- Code here runs immediately after the scene goes entirely off screen
 
    end
end
 
 
-- destroy()
function scene:destroy( event )
 
    local sceneGroup = self.view
    audio.dispose( backgroundMusic )
    -- Code here runs prior to the removal of scene's view
 
end
 
 
-- -----------------------------------------------------------------------------------
-- Scene event function listeners
-- -----------------------------------------------------------------------------------
scene:addEventListener( "create", scene )
scene:addEventListener( "show", scene )
scene:addEventListener( "hide", scene )
scene:addEventListener( "destroy", scene )

return scene