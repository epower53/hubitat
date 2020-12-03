# hubitat
Random hubitat projects that are really only useful to me

trinary-furnace.groovy is a modified version of Bruce Ravenel's Contact-Motion app for Hubitat.
It was created to offer an extra icon choice when looking at thermostat operating state in Tile Master 2 (also by Bruce Ravenel).
Normally, when a device property is selected for display using icons in TM2 only 2 icons are allowed. 
However, if the property is also specified to be a numeric value in TM2, we get the option for 3 icons. 
My simple code converts the enum-typed property into an integer-typed property so we can take advantage of the extra icon in TM2.
It does this:
  1) creates a virtual temperature sensor as an integer storage device
  2) subscribes to the thermostatOperatingState of a thermostat device and parses the enum into bins:
  
    a) "cooling" and "pending cool" are assigned a value of 0
    
    b) "idle", "fan only", and "vent economizer" are assigned a value of 2
    
    c) "heating" and "pending heat" are assigned a value of 4
    
  3) In TM2, I subscribe to the virtual temperature sensor and not the thermostat device. This lets me use the temperature field and flip the switch to get the 3rd icon.
  4) I set a "low" threshold of 1 and a "high" threshold of 3, and then assign icons for cooling, fan/idle, and heating.
  5) Profit!
