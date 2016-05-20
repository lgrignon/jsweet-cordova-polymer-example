package org.jsweet.examples.polymerjs.components;

import static def.polymer_ts.polymer.Globals.createElement;
import static jsweet.dom.Globals.console;
import static jsweet.dom.Globals.localStorage;
import static jsweet.util.Globals.function;
import static jsweet.util.Globals.object;

import java.util.function.Function;

import def.polymer_ts.polymer.Base;
import jsweet.dom.HTMLElement;
import jsweet.lang.Interface;

interface PaperDialog {
	void open();

	void close();
}

@Interface
abstract class PaperItemModel<TItem> {
	TItem item;
}

@Interface
abstract class PaperItemEvent<TItem> {
	PaperItemModel<TItem> model;
}

class Task {
	public String taskName;
	public boolean isComplete;
}

public class TasksListComponent extends Base {

	public TasksListComponent() {
		this.is = "tasks-list";

		console.log("created " + is);
	}

	public static void main() {
		createElement((Function<Object, TasksListComponent>) function(TasksListComponent.class));
		console.log("registered task list component");
	}

	public String latestTaskName;
	public Task[] tasks = {};

	public void showAddTaskDialog() {
		PaperDialog dialog = this.findInnerElement("addTaskDialog");
		dialog.open();
	}
	
	@Override
	public void attached() {
		updateTasks();
	}

	public void addTask() {
		console.log("add task: " + latestTaskName);

		// Store the new task as not completed
		localStorage.setItem(this.latestTaskName, "no");

		// Reset latestTaskName
		this.latestTaskName = "";

		// Close the dialog
		PaperDialog dialog = this.findInnerElement("addTaskDialog");
		dialog.close();

		// Update the list of tasks
		this.updateTasks();
	}

	public void toggleTask(PaperItemEvent<Task> event) {
		// Get the name of the task
		String taskName = event.model.item.taskName;
		console.log("toggle task: " + taskName);

		// Convert true/false to yes/no
		if (event.model.item.isComplete) {
			localStorage.setItem(taskName, "yes");
		} else {
			localStorage.setItem(taskName, "no");
		}
	}

	public void deleteTask(PaperItemEvent<Task> event) {
		String taskName = event.model.item.taskName;
		console.log("remove task: " + taskName);
		
		localStorage.removeItem(taskName);

		// Update the list of tasks
		this.updateTasks();
	}

	public void updateTasks() {
		console.log("refresh tasks");
		
		// Empty the array
		this.splice("tasks", 0, tasks.length);

		// Add items from localStorage
		for (String savedTaskName : jsweet.lang.Object.keys(localStorage)) {
			console.log("restore task: " + savedTaskName);
			Task task = new Task() {
				{
					taskName = savedTaskName;
					isComplete = localStorage.getItem(savedTaskName) == "yes";
				}
			};
			this.push("tasks", task);
		}
	}

	protected <T> T findInnerElement(String id) {
		return (T) object($).$get(id);
	}
}
