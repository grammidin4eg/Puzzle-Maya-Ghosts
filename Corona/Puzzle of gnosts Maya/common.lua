local common = {}

common.screenWidth = 800
common.screenHeight = 1200
common.cellXCount = 7
common.cellYCount = 7
common.cellSize = common.screenWidth / common.cellXCount
common.margin = (common.screenHeight - common.screenWidth) / 2

local function printObj(text, obj)
	print('print '..text..' obj: '..tostring(obj))
	for i,v in pairs(obj) do
		print('>> key: '..i..' value: '..tostring(v))
	end
end
common.printObj = printObj
return common