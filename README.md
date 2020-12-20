# SCC210BattleToTheCore
Terraria Like RPG

Program Structure
- Main creates an instance of Game

- Game is the spine of the program
    The Game is split into 5 key graphical sections: Background, Foreground, Player, Shaders and GUI (in draw layer order, so GUI is ontop of everything else)
        -Background is the map, walls etc (outputted as a vertexArray)
        -Foreground is entities so enemies and objects
        -Player is basically a spicy entity, user controls, Camera  (Player is currently just sitting Game class)
        -Shader does lighting and is applied to all layers below
        -GUI is the interface with the healthbar, minimap etc
        
    The Game then has Math based background sectins such as phyiscs and dataTypes (All of which are basically not implemented)

    In Game we:
        -Creates all the instances of key objects
        -Have Main infinite GameLoop
            -Event handling
            -AT THE BOTTOM OF GAME IS THE DRAWING OF EACH FRAME
                -Creating Shader
        
        -----
        In Game you can change the value of "viewSize" to zoom in and out
                you can change the value of "numberofChunkX" or Y to create a bigger or small map on spawn but becareful anything more than 30 total chunks can be hard on most PCs

- 5 Key Graphical Sections

    Background:
        -Map Gen (does map generation run at the start of the game to create the map)  (Will need to be updated to generate a new map every level based on level difficulty)
            -Map creates the map given the standards requested by Game
            -A Map is made up of an array of Chunks. A Chunk is made up of an array of Blocks
                -The Map is created via MapCreation and using Cellular Automata for simple procedural generation (Will need to update to include perlin noise for entity placement and more diverse creations)
            -ChunkLoader: The map is very big and generated at the start but we dont need to draw the whole map every frame so ChunkLoader takes a renderDistance and generates the visible area for the player

- To do List:
    -Update and stylize worldCreation
    -Fix and finish Lighting
    -Fix file paths
    -Implement ForeGround
    -Implement ability to add other entities
    -Implement A* pathing algoritms for enemy entities
    -Fix Physics
    -Implement Items, Spells and Talents
    -Create Menus

Current Bug List:

    