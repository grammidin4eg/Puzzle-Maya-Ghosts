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
templates[1] = add('back', 'back.png', 'right', 1)
templates[2] = add('wall', 'wall.png', 'right', 1)
templates[3] = add('wall', 'wall2.png', 'right', 1)
templates[4] = add('spirit', 'spirit.png', 'right', 63)
templates[5] = add('player', 'ball.png', 'right', 20)
-- right, left, top, bottom
-- back, wall, wall2, invisible-wall, change-start, change-end, change-timer
-- helper, last-level, player, spirit, bomb, arrow
local function get(id)
    return templates[id]
end
levelTemplates.get = get
return levelTemplates