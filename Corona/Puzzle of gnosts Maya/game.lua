local common = require("common")
local render = require("render")
local composer = require ("composer")
local matrix = require ("matrix")

local scene = composer.newScene()
function scene:create( event )
    print('game create')
    local sceneGroup = self.view
    local params = event.params
     local back = display.newRect(sceneGroup, display.contentCenterX, display.contentCenterY, common.screenWidth, common.screenHeight*2 )
     back:setFillColor( 0, 0, 0 )

    -- очки
    -- local tapText = display.newText( sceneGroup, 'score: 0', 105, 0, native.systemFont, 50)
    -- tapText:setFillColor( 0, 1, 0 )

    -- верхняя картинка
    local top = display.newImageRect(sceneGroup, 'top.png', common.screenWidth, common.margin)
    top.x = display.contentCenterX
    top.y = common.margin / 2 - 10

    local top_sub = display.newImageRect(sceneGroup, 'top_sub.png', common.screenWidth, 12)
    top_sub.x = display.contentCenterX
    top_sub.y = common.margin - 2
    -- нижняя картинка
    local bottom_sub = display.newImageRect(sceneGroup, 'bottom_sub.png', common.screenWidth, 12)
    bottom_sub.x = display.contentCenterX
    bottom_sub.y = common.screenHeight - common.margin + 6

    local bottom = display.newImageRect(sceneGroup, 'bottom.png', common.screenWidth, common.margin)
    bottom.x = display.contentCenterX
    bottom.y = common.screenHeight - common.margin / 2 + 12

    -- отрисовка сетки
    render.gridLines(sceneGroup)

    -- вывод объектов на экран
    local gData = render.drawObjs(params.level, sceneGroup)

    -- подписка на события
    -- https://docs.coronalabs.com/guide/events/touchMultitouch/index.html
    -- выбранный шар
    local selectedPlayer = nil
    local selectedGPlayer = nil
    -- стрелка
    local marker = display.newImageRect(sceneGroup, 'marker.png', common.cellSize, common.cellSize * 1.5)
    marker.x = -100
    marker.y = -100
    -- куда перемещаемся
    local direction = 'none'
    local function touchListener( event )
 
        if ( event.phase == "began" ) then
            local gObj = matrix.findElementWithCoord(gData, event.x, event.y)
            if gObj and gObj.type == 'player' and gObj.obj then
                selectedPlayer = gObj.obj
                selectedGPlayer = gObj
                selectedPlayer.width = selectedPlayer.width * 1.5
                selectedPlayer.height = selectedPlayer.height * 1.5
            end
        elseif ( event.phase == "moved" ) then
            -- event.xStart / event.yStart math.abs
            if selectedPlayer then
                local divX = math.abs(event.x - event.xStart)
                local divY = math.abs(event.y - event.yStart)
                local div = divX
                local neg = 1
                if divX > common.cellSize / 2 or divY > common.cellSize / 2 then
                    if divX > divY then
                        direction = 'right'
                        if event.x < event.xStart then
                            neg = -1
                            direction = 'left'
                        end
                        marker.rotation = 90 * neg
                        marker.x = selectedPlayer.x + neg * common.cellSize / 2
                        marker.y = selectedPlayer.y
                    else
                        
                        if event.y < event.yStart then
                            neg = -1
                            marker.rotation = 0
                            direction = 'top'
                        else
                            marker.rotation = 180
                            direction = 'bottom'
                        end
                        div = divY
                        marker.x = selectedPlayer.x
                        marker.y = selectedPlayer.y + neg * common.cellSize / 2
                    end

                    if div > common.cellSize * 1.5 then
                        div = common.cellSize * 1.5
                    end
                    if div < common.cellSize / 2 then
                        div = common.cellSize / 2
                    end
                    marker.height = div
                    selectedPlayer:toFront()
                else
                    marker.x = -100
                    direction = 'none'
                end
            end
        elseif ( event.phase == "ended" ) then
            if selectedPlayer then
                selectedPlayer.width = selectedPlayer.width / 1.5
                selectedPlayer.height = selectedPlayer.height / 1.5
                if direction ~= 'none' then
                    matrix.moveBall(gData, selectedGPlayer, direction)
                end
                selectedPlayer = nil
                selectedGPlayer = nil
                marker.x = -100
            end
        end
        return true  -- Prevents tap/touch propagation to underlying objects
    end
    back:addEventListener( "touch", touchListener )
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