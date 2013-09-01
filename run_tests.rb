#!/bin/sh ruby

`say Oh!`

result = if system "gradle test"
  'Fabulous'
else
  'Something borked'
end

`say #{result}`

exit 0
