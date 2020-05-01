local common = require("common")
local levelTemplates = require("levels.level_templates")
-- контроль матрицы
local matrix={}
function findElementWithCoord(data, x, y)
    -- print('find x:'..x..'  y:'..y)
    for key, value in ipairs(data) do
        local obj = value['obj']
        local delta = common.cellSize / 2
        if value then
            if x > value.x - delta and x < value.x + delta 
                and y > value.y - delta and y < value.y + delta then
                return value
            end
        end
        --print(key..' >> obj: '..value.type..'  x: '..tostring(value.x)..'  y: '..tostring(value.y))
    end
    return nil
end
function objPosition(obj, time)
    print('objPosition row: '..obj.row..' col: '..obj.col)
    local index = obj.row + 1 + (obj.col * 7)
    return {
        x = obj.x,
        y = obj.y,
        time = time,
        row=obj.row,
        col=obj.col,
        index=index
    }
end
function getFrom(data, row, col)
    local index = row + 1 + (col * 7)
    return data[index]
end
function goToDirection(data, row, col, direction)
    local newRow = row
    local newCol = col

    if direction == 'left' then
        newRow = row - 1
        if newRow < 0 then
            return nil
        end
    elseif direction == 'right' then
        newRow = row + 1
        if newRow > (common.cellXCount - 1) then
            return nil
        end
    elseif direction == 'top' then
        newCol = col - 1
        if newCol < 0 then
            return nil
        end
    elseif direction == 'bottom' then
        newCol = col + 1
        if newRow > (common.cellYCount - 1) then
            return nil
        end
    end

    local newObj = getFrom(data, newRow, newCol)
    if newObj == nil then
        return nil
    end
    if levelTemplates.isWall(newObj) then
        return nil
    end
    return newObj
end
function moveBall(data, gObj, direction, onfindBonus)
    -- print('move to '..direction..' obj: '..tostring(gObj))
    -- print('row '..gObj.row..' col: '..gObj.col)
    --for key, value in ipairs(data) do
        --print('>> row: '..value.row..' col: '..value.col..' index: '..key)
        --print('index test: row: '..data[key].row..' col: '..data[key].col)
    --end
    local row = gObj.row
    local col = gObj.col
    local lastObj = gObj
    local curPos = goToDirection(data, row, col, direction)
    local rows = 0
    while curPos and rows < common.cellXCount do
        lastObj = curPos
        curPos = goToDirection(data, row, col, direction)
        if curPos then
            row = curPos.row
            col = curPos.col
        end
        -- print('goToDirection row: '..row..' col: '..col..' curPos: '..tostring(curPos))
        rows = rows + 1
        if curPos and curPos.type == 'spirit' then 
            print('find in matrix')
            onfindBonus(curPos, rows * 200)
        end
    end
    if lastObj.row == gObj.row and lastObj.col == gObj.col then
        return nil
    else
        return objPosition(lastObj, rows * 200)
    end
end
matrix.findElementWithCoord = findElementWithCoord
matrix.moveBall = moveBall
return matrix