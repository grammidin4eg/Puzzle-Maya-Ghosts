local levelTemplates = {}
local templates = {}
local function add(type, image, direction, frames)
    local template = {}
    template['type'] = type;
    template['image'] = image;
    template['direction'] = direction;
    template['frames'] = frames;
    return template
end
templates[1] = add('back', 'img/back.png', 'right', 1)
templates[2] = add('wall', 'img/wall.png', 'right', 1)
templates[3] = add('wall', 'img/wall2.png', 'right', 1)
templates[4] = add('spirit', 'img/spirit.png', 'right', 63)
templates[5] = add('player', 'img/ball.png', 'right', 20)
templates[6] = add('helper', 'img/back.png', 'right', 1)
-- right, left, top, bottom
-- back, wall, wall2, invisible-wall, change-start, change-end, change-timer
-- helper, last-level, player, spirit, bomb, arrow
local function get(id)
    return templates[id]
end
local function isWall(gObj)
    return ((gObj.type == 'wall') or (gObj.type == 'player'))
end
levelTemplates.get = get
levelTemplates.isWall = isWall
return levelTemplates