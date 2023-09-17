package system

class ZulTagLib {

	static defaultEncodeAs = [taglib:'none']
	static namespace = 'z'

	def center = { attrs, body ->
		out << '<center border="normal" autoscroll="true" style="padding: 5px"><vlayout>'
		out << body()
		out << '</vlayout></center>'
	}

	def south = { attrs, body ->
		out << '<south border="none"><hlayout>'
		out << body()
		out << '</hlayout></south>'
	}

	def button = { attrs, body ->
		def label = g.message(code: attrs.label) ?: ''
		def icon = attrs.icon ? "z-icon-${attrs.icon}" : ''
		def forward = attrs.forward ?: ''
		out << "<button id=\"${attrs.id}\" label=\"${label}\" iconSclass=\"${icon}\" forward=\"${forward}\" />"
	}
}
