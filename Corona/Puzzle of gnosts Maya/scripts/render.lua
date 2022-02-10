local common = require("scripts.common")
local render = {}

-- анимация плавного исчезновения/появления
local function onFadeIn(obj )
    transition.fadeOut( obj , { time=1500, onComplete=render.onFadeOut } )
end
local function onFadeOut(obj)
    transition.fadeIn( obj , { time=1500, onComplete=render.onFadeIn } )
end

render.onFadeIn = onFadeIn
render.onFadeOut = onFadeOut

-- анимация плавного исчезновения/появления
local function onSpiritEffect(obj)
    transition.to( obj , { time=5000, alpha=0.4, yScale=0.85, xScale=0.85, onComplete=render.onSpiritEffectOut } )
end
local function onSpiritEffectOut(obj)
    transition.to( obj , { time=2000, alpha=1, yScale=1, xScale=1, onComplete=render.onSpiritEffect } )
end

render.onSpiritEffect = onSpiritEffect
render.onSpiritEffectOut = onSpiritEffectOut

-- отрисовка сетки
local function gridLines(group)
    local grid = display.newLine(group, 0, common.margin, common.screenWidth, common.margin )
    for i = 1, common.cellYCount do
        grid:append(common.screenWidth, common.margin + common.cellSize * i)
        grid:append(0, common.margin + common.cellSize * i)
        grid:append(common.screenWidth, common.margin + common.cellSize * i)
    end
    grid:append(common.screenWidth, common.margin)
    for i = 0, common.cellXCount do
        grid:append(common.cellSize * i, common.margin)
        grid:append(common.cellSize * i, common.screenHeight - common.margin)
        grid:append(common.cellSize * i, common.margin)
    end
    grid:setStrokeColor( 0.7, 0.7, 0.7, 1 )
    grid.strokeWidth = 3
end

local bonusesObjs = {}
local function bonuses(group, startY)
    for x = 0, common.cellXCount - 1 do
        render.drawCell(group, "wall", "topleft", x * common.cellSize, startY)
    end
    
    for i = 1, 4 do
        bonusesObjs[i] = display.newImageRect(group, 'img/mask_off.png', 66, 88)
        if i < 3 then
            bonusesObjs[i].x = (i - 1) * common.cellSize + common.cellSize / 2
        else
            bonusesObjs[i].x = (i + 2) * common.cellSize + common.cellSize / 2
        end        
        bonusesObjs[i].y = startY - common.cellSize
    end  
    return startY + common.cellSize
end

local function updateBonus(group, value)
    local index = 4 - value
    local maskOn = display.newImageRect(group, 'img/mask_on.png', 66, 88)
    maskOn.x = bonusesObjs[index].x
    maskOn.y = bonusesObjs[index].y
    bonusesObjs[index].x = -100
end

local function gotoBonus(obj, value, delay)
    local index = 4 - value
    obj:toFront()
    -- print('gotoBonus: ', obj, value, delay, index)
    -- print('obj', bonusesObjs, bonusesObjs[index])
    transition.moveTo(obj, {time=1000, x = bonusesObjs[index].x, y = bonusesObjs[index].y, delay=delay})
end

local function indexToPos(index)
    return {
        x=1,
        y=1
    }
end

-- отрисовка объектов
local function drawObjs(data, group, startY)
    local gData = {}
    local my = startY - common.cellSize
    for y = 0, common.cellYCount-1 do
        for x = 0, common.cellXCount-1 do
            local index = (y * 7) + x + 1
            -- обычная картинка или анимация
            if data[index]['frames'] > 1 then
                local back = display.newImageRect(group, 'img/back.png', common.cellSize, common.cellSize)
                back.x = common.cellSize / 2 + (x * common.cellSize)
                back.y = my + (y * common.cellSize)

                local imageSheet = graphics.newImageSheet(data[index]['image'],  {
                    width = 66,
                    height = 66,
                    numFrames = data[index]['frames']
                })
                local sequenceData = {
                    name="run",
                    start=1,
                    count=data[index]['frames']-1,
                    time=3000,
                    loopCount = 0,   -- Optional ; default is 0 (loop indefinitely)
                    loopDirection = "bounce"    -- Optional ; values include "forward" or "bounce"
                }
                local obj = display.newSprite( group, imageSheet, sequenceData )
                obj.x = common.cellSize / 2 + (x * common.cellSize)
                obj.y = my + (y * common.cellSize)
                -- table.insert(gData, {x=obj.x, y=obj.y,type=data[index]['type']})
                --obj:setFrame(1)
                --obj:play()
                --obj.width = common.cellSize
                --obj.height = common.cellSize
                gData[index] = {x=obj.x, y=obj.y,type=data[index]['type'],obj=obj,row=x,col=y}
            else
                local objX = common.cellSize / 2 + (x * common.cellSize)
                local objY = my + (y * common.cellSize)
                if data[index]['hasBackground'] == true then
                    local backObj = display.newImageRect(group, 'img/back.png', common.cellSize, common.cellSize)
                    backObj.x = objX
                    backObj.y = objY
                end
                local obj = display.newImageRect(group, data[index]['image'], common.cellSize, common.cellSize)
                obj.x = objX
                obj.y = objY
                if data[index]['type'] == "spirit" then
                    render.onSpiritEffect(obj)
                end
                gData[index] = {x=obj.x, y=obj.y,type=data[index]['type'],obj=obj,row=x,col=y}
            end
            -- https://docs.coronalabs.com/guide/media/spriteAnimation/index.html
            --for key, value in pairs(gData) do
        end
    end
    return gData;
end

local function fillDisplay(sceneGroup)
    local back = display.newRect(sceneGroup, display.contentCenterX, display.contentCenterY, common.screenWidth, common.screenHeight*2 )
    back:setFillColor( render.fromRGB(181), render.fromRGB(213), render.fromRGB(245) )
    return back
end

local function fromRGB(value)
    return value/255
end

local function drawImg(sceneGroup, filename, width, height, anchor, x, y)
    local img = display.newImageRect(sceneGroup, 'img/'..filename..'.png', width, height)
    if anchor == "top" then
        img.anchorY = 0
    end
    if anchor == "topleft" then
        img.anchorY = 0
        img.anchorX = 0
    end
    img.x = x
    img.y = common.topmargin + y
    return img
end

local function drawCell(group, name, anchor, x, y)
    return render.drawImg(group, name, common.cellSize, common.cellSize, anchor, x, y)
end

render.gridLines = gridLines
render.drawObjs = drawObjs
render.bonuses = bonuses
render.updateBonus = updateBonus
render.gotoBonus = gotoBonus
render.drawImg = drawImg
render.fillDisplay = fillDisplay
render.drawCell = drawCell
render.fromRGB = fromRGB

return render