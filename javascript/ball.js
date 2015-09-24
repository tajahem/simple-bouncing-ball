"use strict";

// determines if the change would remain within range and inverts it if not
var getChange = function (current, change, min, max) {
    if (current + change > max || current + change < min) {
        change = -change;
    }
    return change;
};

// used to store and format color data red, green, and blue channels should be
// in range 0-255 alpha should be in range 0.0-1.0
var Color = function (r, g, b, a) {
    this.channels = [r, g, b];
    var alpha = a;
    this.formatAsCSS = function () {
        return "rgba(" + this.channels[0] + "," + this.channels[1] +
            "," + this.channels[2] + "," + alpha + ")";
    };
    this.toString = function () {
        return "color[r=" + this.channels[0] + "g=" + this.channels[1] +
            "b=" + this.channels[2] + "a=" + alpha + "]";
    };
};

// used to alter a color within range by a set amount per channel
var ColorChanger = function (r, g, b) {
    var changeAmounts = [r, g, b];
    this.alterColor = function (color) {
        var i;
        for (i = 0; i < changeAmounts.length; i += 1) {
            changeAmounts[i] = getChange(color.channels[i], changeAmounts[i],
                                         0, 255);
            color.channels[i] += changeAmounts[i];
        }
    };
};

// used to alter a position
var Velocity = function (x, y, m) {
    this.x = x;
    this.y = y;
    var max = m;
    this.adjustHorizontal = function (amount) {
	    if (getChange(this.x, amount, -max, max) === amount) {
		    this.x += amount;
	    }
    };
    this.adjustVertical = function (amount) {
        if (getChange(this.y, amount, -max, max) === amount) {
		    this.y += amount;
	    }
    };
    this.toString = function () {
        return "velocity[x=" + this.x + "y=" + this.y +
            "max=" + max + "]";
    };
};

// position with min/max values to ensure ball stays within canvas bounds
var Position = function (x, y, mX, mY, mxX, mxY) {
    this.xPos = x;
    this.yPos = y;
    var minX = mX,
        minY = mY,
        maxX = mxX,
        maxY = mxY;
    this.update = function (velocity) {
        velocity.x = getChange(this.xPos, velocity.x, minX, maxX);
        this.xPos += velocity.x;
        velocity.y = getChange(this.yPos, velocity.y, minY, maxY);
        this.yPos += velocity.y;
    };
    this.toString = function () {
        return "position[x=" + this.xPos + "y=" + this.yPos + "]";
    };
};


// an intergalactic portal of catastrophic proportion... 
// or maybe just a color changing ball with a little highlight. Your call.
var Ball = function (size, areaWidth, areaHeight) {
    this.velocity = new Velocity(0, 0, areaWidth / 10);
    var position = new Position(Math.floor(areaWidth / 2),
                                Math.floor(areaHeight / 2), size, size,
                                areaWidth - size, areaHeight - size),
        color = new Color(255, 175, 0, 1.0),
        highlight = new Color(255, 255, 255, 0.5),
        changer = new ColorChanger(-1, 1, 2),
        radius = size,
        that = this,
	    // private function to draw a very simple highlight        
	    drawHighlight = function (context) {
            context.strokeStyle = highlight.formatAsCSS();
            context.beginPath();
            context.lineCap = "round";
            context.lineWidth = Math.floor(radius / 10);
            context.stroke();
            context.arc(position.xPos, position.yPos,
                        radius * 0.75, 1.6 * Math.PI, 0, false);
            context.stroke();
        };
    // update position and alter the color
    this.update = function () {
        position.update(this.velocity);
        changer.alterColor(color);
    };
    // draw the background, then a border, and lastly a highlight
    this.draw = function (context) {
        context.fillStyle = color.formatAsCSS();
        context.beginPath();
        context.arc(position.xPos, position.yPos,
                    radius, 0, 2 * Math.PI, false);
        context.fill();
        context.lineCap = "round";
        context.lineWidth = 1;
        context.strokeStyle = "black";
        context.stroke();
        drawHighlight(context);
    };
    this.toString = function () {
        return "ball[" + position.toString() + color.toString() +
            this.velocity.toString() + "]";
    };
};

//      Variables      //
var timer;
var ball;
var timeOffset = 16;
var ballSize = 20;
var canvas = document.getElementById("bounce");
var context = canvas.getContext("2d");

// clear the canvas and redraw
var draw = function () {
    context.clearRect(0, 0, canvas.width, canvas.height);
    ball.draw(context);
};

// update and reset timer
var update = function () {
    ball.update();
    timer = setTimeout(function () {update.apply(); draw(); }, timeOffset);
};

// generates an html button
var getButton = function (id, action, label) {
    return '<button id="' + id + '" onclick="' + action + '()">' +
            label + '</button>\n';
};

//   Public Functions  //
// create the controls and the ball, start the timer, and begins draw calls
function start() {
    var controls = document.getElementById("controls"),
        divs = '<div style="padding:3px">\n',
        fullControls = divs + getButton("up", "increaseYVelocity", "^") +
            "</div>\n" + getButton("left", "decreaseXVelocity", "<") +
            getButton("down", "decreaseYVelocity", "v") +
            getButton("right", "increaseXVelocity", ">") +
            divs + getButton("stop", "stop", "Stop") + "</div>";
    ball = new Ball(ballSize, canvas.width, canvas.height);
    timer = setTimeout(function () {update.apply(); draw(); }, timeOffset);
    controls.innerHTML = fullControls;
}

// stops all draw and update calls and clears the ball and timer
function stop() {
    var controls = document.getElementById("controls");
    clearTimeout(timer);
    ball = null;
    timer = null;
    controls.innerHTML = getButton("start", "start", "Start");
}

// velocity modifiers
function increaseXVelocity() {
    ball.velocity.adjustHorizontal(1);
}

function decreaseXVelocity() {
    ball.velocity.adjustHorizontal(-1);
}

function increaseYVelocity() {
    ball.velocity.adjustVertical(-1);
}

function decreaseYVelocity() {
    ball.velocity.adjustVertical(1);
}
