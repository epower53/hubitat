definition(
    name: "Trinary-Thermostat-Status",
    namespace: "hubitat",
    author: "Erik Power (ported from code originally published by Bruce Ravenel",
    description: "Convert thermostatOperatingState into a trinary numeric value using a virtual temperature sensor",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: "")

preferences {
	page(name: "mainPage")
}

def mainPage() {
	dynamicPage(name: "mainPage", title: " ", install: true, uninstall: true) {
		section {
			input "thisName", "text", title: "Name for the virtual Trinary furnace status device", submitOnChange: true
			if(thisName) app.updateLabel("$thisName")
			input "thermoStats", "capability.thermostat", title: "Select Thermostat", submitOnChange: true, required: true, multiple: true
		}
	}
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	def thermoDev = getChildDevice("TrinaryThermo_${app.id}")
	if(!thermoDev) thermoDev = addChildDevice("hubitat", "Virtual Temperature Sensor", "TrinaryThermo_${app.id}", null, [label: thisName, name: thisName])
	subscribe(thermoStats, "thermostatOperatingState", handler)
}

def handler(evt) {
	def thermoDev = getChildDevice("TrinaryThermo_${app.id}")
	int thermoState = 0
	switch(evt.value)
	{
		case "cooling":
		case "pending cool":
			thermoState = 0
			break
		case "idle":
		case "fan only":
		case "vent economizer":
			thermoState = 2
			break
		case "heating":
		case "pending heat":
			thermoState = 4
			break
		default:
			thermoState = 0
			break
	}
	thermoDev.setTemperature(thermoState)
	log.info "TrinaryThermo $evt.device $evt.value"
}
