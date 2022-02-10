local levelTemplates = {}
local templates = {}
local function add(type, image, frames, direction, hasBackground)    
    local template = {}
    template['type'] = type;
    template['image'] = image;
    template['direction'] = direction or 'right';
    template['frames'] = frames or 1;
    template['hasBackground'] = hasBackground or false;
    return template
end
templates[1] = add('back', 'img/back.png')
templates[2] = add('wall', 'img/wall.png')
templates[3] = add('wall', 'img/wall2.png')
templates[4] = add('spirit', 'img/spirit_mask.png', 1, 'right', true)
templates[5] = add('player', 'img/ball.png', 20)
templates[6] = add('helper', 'img/back.png')
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