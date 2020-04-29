local common = require("common")
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

local function indexToPos(index)
    return {
        x=1,
        y=1
    }
end

-- отрисовка объектов
local function drawObjs(data, group)
    local gData = {}
    for y = 0, common.cellYCount-1 do
        for x = 0, common.cellXCount-1 do
            local index = (y * 7) + x + 1
            -- обычная картинка или анимация
            if data[index]['frames'] > 1 then
                local back = display.newImageRect(group, 'back.png', common.cellSize, common.cellSize)
                back.x = common.cellSize / 2 + (x * common.cellSize)
                back.y = common.margin + (common.cellSize / 2) + (y * common.cellSize)

                local imageSheet = graphics.newImageSheet(data[index]['image'],  {
                    width = 66,
                    height = 66,
                    numFrames = data[index]['frames']
                })
                local sequenceData = {
                    name="run",
                    start=1,
                    count=data[index]['frames']-1,
                    time=5000,
                    loopCount = 0,   -- Optional ; default is 0 (loop indefinitely)
                    loopDirection = "bounce"    -- Optional ; values include "forward" or "bounce"
                }
                local obj = display.newSprite( group, imageSheet, sequenceData )
                obj.x = common.cellSize / 2 + (x * common.cellSize)
                obj.y = common.margin + (common.cellSize / 2) + (y * common.cellSize)
                -- table.insert(gData, {x=obj.x, y=obj.y,type=data[index]['type']})
                --obj:setFrame(1)
                --obj:play()
                --obj.width = common.cellSize
                --obj.height = common.cellSize
                gData[index] = {x=obj.x, y=obj.y,type=data[index]['type'],obj=obj}
            else
                local obj = display.newImageRect(group, data[index]['image'], common.cellSize, common.cellSize)
                obj.x = common.cellSize / 2 + (x * common.cellSize)
                obj.y = common.margin + (common.cellSize / 2) + (y * common.cellSize)
                gData[index] = {x=obj.x, y=obj.y,type=data[index]['type'],obj=obj}
            end
            -- https://docs.coronalabs.com/guide/media/spriteAnimation/index.html
            --for key, value in pairs(gData) do
        end
    end
    return gData;
end
render.gridLines = gridLines
render.drawObjs = drawObjs

return render