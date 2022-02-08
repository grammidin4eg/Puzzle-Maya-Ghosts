local composer = require( "composer" )

local levelManager = {}
local function loadLevel(lvl)
    print('load level', lvl)
    local level = require('levels.level_' .. lvl)
    print('data', level)
    composer.gotoScene( "scripts.game", { params={ level=level } } )
end
levelManager.loadLevel = loadLevel
return levelManager