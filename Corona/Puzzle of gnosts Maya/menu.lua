local common = require("common")
local render = require("render")
local composer = require ("composer")

local scene = composer.newScene()
-- https://github.com/coronalabs/Match-Three-Space-RPG/blob/master/scene/menu.lua
function scene:create( event )
    print('menu create')
    local sceneGroup = self.view
    local myRectangle = display.newRect(sceneGroup, display.contentCenterX, display.contentCenterY, common.screenWidth, common.screenHeight*2 )
    myRectangle:setFillColor( 0.4, 0.4, 1 )

    -- заголовок
    local titleText = display.newText( sceneGroup, 'LINES', display.contentCenterX, display.contentCenterY-90, native.systemFont, 180 )
    local gradient = {
        type="gradient",
        color1={ 0.5, 0.5, 1 }, color2={ 1.00,0.55,0.10 }, direction="down"
    }
    titleText:setFillColor( gradient )

    -- текст снизу
    local tapText = display.newText( sceneGroup, 'tap to play', display.contentCenterX, display.contentCenterY + 190, native.systemFont, 50)
    tapText:setFillColor( gradient )
    render.onFadeIn(tapText)

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