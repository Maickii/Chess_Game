# Chess_Game

## Play a java chess game against your friends!

_Note:_ This project is still under development

### Game includes:
1.	Only play during your turn!
2.	Only legal move allowed, no moving into your own friendly territory
3.	Capture system
4.	Each piece moves as they are supposed to move
    + Pawn moves forward twice in the beginning, one tile thereafter
    + Rook moves horizontally and vertically
    + Bishop moves diagonally both ways, within their starting tile
    + Knight moves in ‘L’ shape
    + King moves within a radius of one tile
    + Queen moves as a combination of Rook and Bishop



### Known bugs and things that need further development:
1.	Threat system. Currently, if the king gets checked, nothing happens
2.	Same thing with pined pieces. Legally, pined pieces cannot move, but that has not been developed yet
3.	Special moves:
    - Castling: simultaneously move the king and rook to create a barricade
    - Promote Pawns once they reach the other side of the board
    - En passant. This move legally allows a pawn to capture another pawn when it double steps.
4.	Game over when win/loss/tie
