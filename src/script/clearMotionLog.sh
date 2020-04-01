#!/bin/bash
echo "empty log file /var/log/motion/motion.log"

rm /var/log/motion/motion.log
touch /var/log/motion/motion.log
chmod a+w /var/log/motion/motion.log
chmod a+r /var/log/motion/motion.log
chmod a+x /var/log/motion/motion.log
