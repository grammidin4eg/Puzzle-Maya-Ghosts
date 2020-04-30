local common = require("common")
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
function objPosition(obj)
    print('objPosition row'..obj.row..' col'..obj.col)
    return {
        x = obj.x,
        y = obj.y
    }
end
function getFrom(data, row, col)
    local index = row + 1 + (col * 7)
    return data[index]
end
function goToDirection(data, row, col, direction)
    return nil
end
function moveBall(data, gObj, direction)
    print('move to '..direction..' obj: '..tostring(gObj))
    print('row '..gObj.row..' col: '..gObj.col)
    --for key, value in ipairs(data) do
        --print('>> row: '..value.row..' col: '..value.col..' index: '..key)
        --print('index test: row: '..data[key].row..' col: '..data[key].col)
    --end
    local row = gObj.row
    local col = gObj.col
    local lastObj = gObj
    local curPos = goToDirection(data, row, col, direction)
    while curPos do
        
    end
    if lastObj.row == gObj.row and lastObj.col == gObj.col then
        return nil
    else
        return objPosition(lastObj)
    end
end
matrix.findElementWithCoord = findElementWithCoord
matrix.moveBall = moveBall
return matrix