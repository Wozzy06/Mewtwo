# .*
# join,nickchange

require 'parseconfig'

config = ParseConfig.new('memos.cfg')
prefix = ParseConfig.new('mewtwo.cfg')['prefix']

memos = config[ARGV[0]]

exit unless memos

puts "#{ARGV[0]}, you have #{memos.length} memos. Do '#{prefix}memo get' to read them"
