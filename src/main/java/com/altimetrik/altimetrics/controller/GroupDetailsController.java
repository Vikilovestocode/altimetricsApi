package com.altimetrik.altimetrics.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.altimetrik.altimetrics.entity.GroupDetails;
import com.altimetrik.altimetrics.pojo.GroupDto;
import com.altimetrik.altimetrics.service.GroupDetailsService;

@RestController
@RequestMapping("/groups")
public class GroupDetailsController {

	@Autowired
	private GroupDetailsService groupDetailsService;
	
	@GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroupDetails() throws IOException {
       return new ResponseEntity<List<GroupDto>>(groupDetailsService.getAllGroupDetails(), HttpStatus.OK);
    }
	
	@GetMapping(value = "/{rallyGroupId}")
    public ResponseEntity<GroupDto> getGroupDetailsById(@PathVariable("rallyGroupId") String rallyGroupId) throws IOException {
       return new ResponseEntity<GroupDto>(groupDetailsService.getGroupDetailsById(rallyGroupId), HttpStatus.OK);
    }
	
	@GetMapping(value = "/name/{groupName}")
    public ResponseEntity<GroupDto> getGroupDetailsByName(@PathVariable("groupName") String groupName) throws IOException {
       return new ResponseEntity<GroupDto>(groupDetailsService.getGroupDetailsByName(groupName), HttpStatus.OK);
    }
	
	@PostMapping
    public ResponseEntity<GroupDto> createGroupDetails(@RequestBody GroupDetails groupDetails) throws IOException {
       return new ResponseEntity<GroupDto>(groupDetailsService.createGroupDetails(groupDetails), HttpStatus.CREATED);
    }
	
	@PutMapping(value = "{rallyGroupId}")
    public ResponseEntity<GroupDto> updateGroupDetails(@PathVariable("rallyGroupId") String rallyGroupId, @RequestBody GroupDetails groupDetails) throws IOException {
       return new ResponseEntity<GroupDto>(groupDetailsService.updateGroupDetails(rallyGroupId, groupDetails), HttpStatus.ACCEPTED);
    }

    @PatchMapping(value = "{rallyGroupId}")
    public ResponseEntity<GroupDto> updateGroupDetailsPatch(@RequestBody GroupDetails groupDetails) throws IOException {
        return new ResponseEntity<GroupDto>(groupDetailsService.updateGroupDetails(groupDetails.getRallyGroupId(), groupDetails), HttpStatus.ACCEPTED);
    }
}
