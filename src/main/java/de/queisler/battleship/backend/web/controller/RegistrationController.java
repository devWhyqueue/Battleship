package de.queisler.battleship.backend.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import de.queisler.battleship.backend.service.UserService;
import de.queisler.battleship.backend.web.dto.UserDto;
import de.queisler.battleship.backend.web.error.UserAlreadyExistException;
import de.queisler.battleship.data.model.User;

@Controller
public class RegistrationController
{
	@Autowired
	private UserService userService;

	@GetMapping(value = "/register")
	public String showRegistrationForm(WebRequest request, Model model)
	{
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);

		return "register";
	}

	@PostMapping(value = "/register")
	public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto, BindingResult result,
		WebRequest request, Errors errors)
	{
		User registered = new User();
		if (!result.hasErrors())
		{
			registered = createUserAccount(accountDto, result);
		}
		if (registered == null)
		{
			result.rejectValue("username", "message.regError");
		}
		if (result.hasErrors())
		{
			return new ModelAndView("register", "user", accountDto);
		}
		else
		{
			return new ModelAndView("registrationSuccess");
		}
	}

	private User createUserAccount(UserDto accountDto, BindingResult result)
	{
		User registered = null;
		try
		{
			registered = userService.registerNewUserAccount(accountDto);
		}
		catch (UserAlreadyExistException e)
		{
			return null;
		}
		return registered;
	}
}
