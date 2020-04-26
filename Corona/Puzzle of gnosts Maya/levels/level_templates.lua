local levelTemplates = {}
local templates = {}
local function add(type, image, direction)
    local template = {}
    template['type'] = type;
    template['image'] = image;
    template['direction'] = direction;
    return template
end
templates[1] = add('back', 'back.png', 'right')
-- right, left, top, bottom
-- back, wall, wall2, invisible-wall, change-start, change-end, change-timer
-- helper, last-level, player, spirit, bomb, arrow
local function get(id)
    return templates[id]
end
levelTemplates.get = get
return levelTemplates