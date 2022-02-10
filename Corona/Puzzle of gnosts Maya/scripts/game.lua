local composer = require ("composer")
local common = require("scripts.common")
local render = require("scripts.render")
local matrix = require ("scripts.matrix")

local scene = composer.newScene()
function scene:create( event )
    print('game create')
    local sceneGroup = self.view
    local params = event.params
    local back = render.fillDisplay(sceneGroup)

    -- очки
    -- local tapText = display.newText( sceneGroup, 'score: 0', 105, 0, native.systemFont, 50)
    -- tapText:setFillColor( 0, 1, 0 )
    -- todo fps

    local my = 0

    -- верхняя картинка
    local top = render.drawImg(sceneGroup, "top", common.screenWidth, common.margin, "top", display.contentCenterX, 0)
    local top_sub = render.drawImg(sceneGroup, "top_sub", common.screenWidth, 12, "top", display.contentCenterX, common.margin)

    -- отрисовка сетки
    -- render.gridLines(sceneGroup)

    my = top.height + top_sub.height

    -- бонусы
    local bonuses = 4
    my = render.bonuses(sceneGroup, my)

    -- вывод объектов на экран
    local gData = render.drawObjs(params.level, sceneGroup, my)
    my = my + common.cellYCount * common.cellSize

    -- нижняя картинка
    local bottom_sub = render.drawImg(sceneGroup, "bottom_sub", common.screenWidth, 12, "top", display.contentCenterX, my)
    my = my + bottom_sub.height
    local bottom = render.drawImg(sceneGroup, "bottom", common.screenWidth, common.margin, "top", display.contentCenterX, my)

    -- события коллизии с бонусом
    local function takeBonus(obj)
        print(common.printObj('takeBonus',obj))
        bonuses = bonuses - 1
        -- счетчик на экране
        render.updateBonus(sceneGroup, bonuses)
        -- todo если меньше 1 - победили
    end

    local function findBonus(gObj, delay)
        print(common.printObj('findBonus', gObj))
        gObj.type = 'back'
        transition.fadeOut( gObj.obj , { time=1500, onComplete=takeBonus, delay=delay } )
        render.gotoBonus(gObj.obj, (bonuses - 1), delay)
    end

    -- подписка на события
    -- https://docs.coronalabs.com/guide/events/touchMultitouch/index.html
    -- выбранный шар
    local selectedPlayer = nil
    local selectedGPlayer = nil
    -- стрелка
    local marker = display.newImageRect(sceneGroup, 'img/marker.png', common.cellSize, common.cellSize * 1.5)
    marker.x = -100
    marker.y = -100
    -- куда перемещаемся
    local direction = 'none'

    -- после перемещения
    local movedBall = nil
    local function onMoveComplete()
        print('move complete: '..tostring(movedBall))
        movedBall:pause()
        movedBall = nil
    end
    -- обработка касания
    local function touchListener( event )
 
        if movedBall then
            return
        end
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
                    local toObj = matrix.moveBall(gData, selectedGPlayer, direction, findBonus)
                    if toObj then
                        -- print('time: ', toObj.time)
                        selectedPlayer:play()
                        movedBall = selectedPlayer
                        selectedPlayer.rotation = marker.rotation
                        transition.moveTo( selectedPlayer, { x=toObj.x, y=toObj.y, time=toObj.time, onComplete=onMoveComplete } )
                        local index = toObj.index
                        gData[index].x = toObj.x
                        gData[index].y = toObj.y
                        gData[index].row = toObj.row
                        gData[index].col = toObj.col
                        gData[index].obj = selectedPlayer
                        gData[index].type = selectedGPlayer.type
                        selectedGPlayer.type = 'back'
                        -- common.printObj('new gData[index]', gData[index])
                    end
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