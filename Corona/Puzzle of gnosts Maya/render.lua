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
    for key, value in pairs(data) do
        local obj = display.newImageRect(group, value['image'], common.cellSize, common.cellSize)
        local pos = indexToPos(key)
        obj.x = common.cellSize / 2 + (pos.x * common.cellSize)
        obj.y = common.margin + (common.cellSize / 2) + (pos.y * common.cellSize)
    end
end
render.gridLines = gridLines
render.drawObjs = drawObjs

return render