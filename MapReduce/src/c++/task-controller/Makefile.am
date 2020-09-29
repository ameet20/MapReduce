ACLOCAL_AMFLAGS = -I ../utils/m4 
AM_CFLAGS = -Wall

bindir = $(exec_prefix)

bin_PROGRAMS = task-controller
check_PROGRAMS = tests/test-task-controller
TESTS = $(check_PROGRAMS)

task_controller_SOURCES = main.c task-controller.c configuration.c \
                          task-controller.h

tests_test_task_controller_SOURCES = tests/test-task-controller.c \
                                     task-controller.c configuration.c task-controller.h

test:	$(check_PROGRAMS)
	@echo Done with $<
