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
matrix.findElementWithCoord = findElementWithCoord
return matrix