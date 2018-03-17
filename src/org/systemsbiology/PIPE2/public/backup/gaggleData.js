/*
 * Copyright (C) 2007 by Institute for Systems Biology,
 * Seattle, Washington, USA.  All rights reserved.
 *
 * This source code is distributed under the GNU Lesser
 * General Public License, the text of which is available at:
 *   http://www.gnu.org/copyleft/lesser.html
 */


/**
 * To support asynchronous calls, set the property 'isAsynch' to true
 * and implement the method 'asynchronouslyFetchData', which takes a
 * callback function as an arguement.
 */
function FG_GaggleData(name, dataType, size, species, data) {
	this._name = name;
	this._type = dataType;
	this._size = size;
	this._species = species;
	this._data = data;
}

FG_GaggleData.prototype.getName = function() {
	return this._name;
}

FG_GaggleData.prototype.getType = function() {
	return this._type;
}

FG_GaggleData.prototype.getSize = function() {
	return this._size;
}

FG_GaggleData.prototype.getSpecies = function() {
	return this._applyDefaultSpecies(this._species);
}

FG_GaggleData.prototype.getDescription = function() {
	return this.getName() + ": " + this.getType() + this._sizeString();
}

FG_GaggleData.prototype._sizeString = function() {
	if (this.getSize())
		return "(" + this.getSize() + ")";
	else
		return "";
}

FG_GaggleData.prototype.getData = function() {
	return this._data;
}

FG_GaggleData.prototype.toString = function() {
	return this.getDescription();
}

/**
 * This method will be "overridden" in the firegoose (by
 * replacing it) which is the least goofy way I could think
 * of to avoid a circular dependency between this and
 * firegoose.js where the real defaulting policy is implemented
 */
FG_GaggleData.prototype._applyDefaultSpecies = function(species) {
	return species;
}



